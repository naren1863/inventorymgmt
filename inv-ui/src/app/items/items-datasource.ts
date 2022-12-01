import { DataSource } from '@angular/cdk/collections';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { map } from 'rxjs/operators';
import { Observable, of as observableOf, merge, BehaviorSubject } from 'rxjs';
import { ItemsItem } from '../models/item';
import { ItemService } from '../providers/item.service';

/**
 * Data source for the Items view. This class should
 * encapsulate all logic for fetching and manipulating the displayed data
 * (including sorting, pagination, and filtering).
 */
export class ItemsDataSource extends DataSource<ItemsItem> {
  //data: ItemsItem[] = [];
  sort: MatSort | undefined;

  _filterChange = new BehaviorSubject('');

  get filter(): string {
    return this._filterChange.value;
  }

  set filter(filter: string) {
    this._filterChange.next(filter);
  }

  filteredData: ItemsItem[] = [];
  renderedData: ItemsItem[] = [];
  totalCount!: number;

  constructor(
    public itemService: ItemService, 
    public _paginator: MatPaginator) 
  {
    super();
    console.log(">> Datasource Constructor: ", this._paginator);
    this._filterChange.subscribe(() => (this._paginator.pageIndex = 0));
    console.log("<< Datasource Constructor: ", this._paginator);
  }

  /**
   * Connect this data source to the table. The table will only update when
   * the returned stream emits new items.
   * @returns A stream of the items to be rendered.
   */
  connect(): Observable<ItemsItem[]> {
    console.log(">> Datasource connect(): ", this._paginator);
    if (this._paginator && this.sort) {
      // Combine everything that affects the rendered data into one update
      // stream for the data-table to consume.
      this.itemService.getAllItems(this._paginator.pageIndex, this._paginator.pageSize);

      return merge(this.itemService.itemChange, this._paginator.page, this.sort.sortChange, this._filterChange)
        .pipe(map(() => {
          console.log(">> Datasource merge(): ", this.itemService.items, this.totalCount);
          //this.data = this.itemService.items;
          this._paginator.length = this.totalCount;

          this.filteredData = this.itemService.items
          .slice()
          .filter((item: ItemsItem) => {
            const searchStr = '';
            if(item){
               const searchStr = (
                item.ItemId?.toString() +
                item.ItemName?.toString() +
                item.ItemDescription?.toString()
              ).toLowerCase();
              return searchStr.indexOf(this.filter.toLowerCase()) !== -1;
            }
            return searchStr.indexOf(this.filter.toLowerCase()) !== -1;
          });
          console.log("<< Datasource merge(): ");
          return this.getPagedData(this.getSortedData([...this.filteredData ]));
        }));
    } else {
      console.log("<< datasource:connect: error" + this.filteredData);
      throw Error('Please set the paginator and sort on the data source before connecting.');
    }
    

  }

  /**
   *  Called when the table is being destroyed. Use this function, to clean up
   * any open connections or free any held resources that were set up during connect.
   */
  disconnect(): void {}

  /**
   * Paginate the data (client-side). If you're using server-side pagination,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getPagedData(data: ItemsItem[]): ItemsItem[] {
    if (this._paginator) {
      console.log("-- getPagedData: this._paginator.pageIndex: ", this._paginator.pageIndex);
      console.log("-- getPagedData: this._paginator.pageSize: ", this._paginator.pageSize);
      const startIndex = this._paginator.pageIndex * this._paginator.pageSize;
      return data.splice(startIndex, this._paginator.pageSize);
    } else {
      return data;
    }
  }

  /**
   * Sort the data (client-side). If you're using server-side sorting,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getSortedData(data: ItemsItem[]): ItemsItem[] {
    if (!this.sort || !this.sort.active || this.sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      const isAsc = this.sort?.direction === 'asc';
      switch (this.sort?.active) {
        case 'name': return compare(a.name, b.name, isAsc);
        case 'id': return compare(+a.id, +b.id, isAsc);
        default: return 0;
      }
    });
  }
}

/** Simple sort comparator for example ID/Name columns (for client-side sorting). */
function compare(a: string | number, b: string | number, isAsc: boolean): number {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
