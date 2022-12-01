import { DataSource } from '@angular/cdk/collections';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { map } from 'rxjs/operators';
import { Observable, of as observableOf, merge, BehaviorSubject } from 'rxjs';
import { ItemCategory } from '../models/itemCategory';
import { ItemCategoryService } from '../providers/itemcategory.service';

/**
 * Data source for the itemCategories view. This class should
 * encapsulate all logic for fetching and manipulating the displayed data
 * (including sorting, pagination, and filtering).
 */
export class ItemCategoryDataSource extends DataSource<ItemCategory> {
  //data: ItemCategory[] = [];
  sort: MatSort | undefined;

  _filterChange = new BehaviorSubject('');

  get filter(): string {
    return this._filterChange.value;
  }

  set filter(filter: string) {
    this._filterChange.next(filter);
  }

  filteredData: ItemCategory[] = [];
  renderedData: ItemCategory[] = [];
  

  constructor(
    public itemCategoryService: ItemCategoryService, 
    public _paginator: MatPaginator) 
  {
    super();
    console.log(">> Datasource Constructor: ", this._paginator);
    this._filterChange.subscribe(() => (this._paginator.pageIndex = 0));
    console.log("<< Datasource Constructor: ", this._paginator);
  }

  /**
   * Connect this data source to the table. The table will only update when
   * the returned stream emits new itemCategories.
   * @returns A stream of the itemCategories to be rendered.
   */
  connect(): Observable<ItemCategory[]> {
    console.log(">> Datasource connect(): ", this._paginator);
    if (this._paginator && this.sort) {
      // Combine everything that affects the rendered data into one update
      // stream for the data-table to consume.
      this.itemCategoryService.getAllItemCategories();
     
      return merge(this.itemCategoryService.itemCategoryChange, this._paginator.page, this.sort.sortChange, this._filterChange)
        .pipe(map(() => {
          console.log(">> Datasource merge(): ", this.itemCategoryService.itemCategories);
          
          this.filteredData = this.itemCategoryService.itemCategories
          .slice()
          .filter((itemCategory: ItemCategory) => {
            const searchStr = '';
            if(itemCategory){
               const searchStr = (
                itemCategory.CategoryName.toString + 
                itemCategory.CategoryCode.toString()
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
  private getPagedData(data: ItemCategory[]): ItemCategory[] {
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
  private getSortedData(data: ItemCategory[]): ItemCategory[] {
    if (!this.sort || !this.sort.active || this.sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      const isAsc = this.sort?.direction === 'asc';
      switch (this.sort?.active) {
        case 'code': return compare(a.CategoryCode, b.CategoryCode, isAsc);
        case 'name': return compare(+a.CategoryName, +b.CategoryName, isAsc);
        default: return 0;
      }
    });
  }
}

/** Simple sort comparator for example ID/Name columns (for client-side sorting). */
function compare(a: string | number, b: string | number, isAsc: boolean): number {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
