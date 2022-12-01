import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { ItemCategoryDataSource} from './itemcategory-datasource';
import { ItemsItem } from '../models/item';
import { ItemCategoryService } from '../providers/itemcategory.service';
import { AddDialogComponent } from '../dialogs/add.dialog/add.dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { RouterLinkWithHref } from '@angular/router';
import { UpdateDialogComponent } from '../dialogs/update.dialog/update.dialog.component';
import { fromEvent, raceWith, Subject } from 'rxjs';
import { DeleteDialogComponent } from '../dialogs/delete.dialog/delete.dialog.component';
import * as XLSX from 'xlsx';
import { DetailDialogComponent } from '../dialogs/detail.dialog/detail.dialog.component';
import { ItemCategory } from '../models/itemCategory';

@Component({
  selector: 'app-category',
  templateUrl: './itemcategory.component.html',
  styleUrls: ['./itemcategory.component.css']
})
export class ItemCategoryComponent implements OnInit, AfterViewInit {
  
  spinnerEnabled = false;
  keys!: string[];
  dataSheet = new Subject<any[]>();
  isExcelFile!: boolean;

  dataSource !: ItemCategoryDataSource;
  selectedItemId: String = "";
  importedData: ItemCategory[] = [];
  
  currentPage: number = 0;
  pageSize: number = 5;
  
  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['CategoryCode', 'CategoryName'];
  exportableColumns = ['CategoryCode', 'CategoryName'];

  constructor(private itemCategoryService: ItemCategoryService, 
              public dialogService: MatDialog) 
  {
  }

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<ItemCategory>;
  @ViewChild('TABLE') exportTable!: ElementRef;
  @ViewChild('inputFile') inputFile!: ElementRef;
  @ViewChild('filter', { static: true }) filter!: ElementRef;

  loadData() {
    //console.log(">> loadData: ", this.paginator);
    this.dataSource = new ItemCategoryDataSource(this.itemCategoryService, this.paginator);

    fromEvent(this.filter.nativeElement, 'keyup')
      // .debounceTime(150)
      // .distinctUntilChanged()
      .subscribe(() => {
        //console.log('>> fromEvent: ');
        if (!this.dataSource) {
          return;
        }
        this.dataSource.filter = this.filter.nativeElement.value;
        //console.log('<< fromEvent: ');
      });
    //console.log('<< loadData: ');
  }

  ngAfterViewInit(): void {
    //console.log(">> ngAfterViewInit", this.paginator);
    this.loadData();
    this.dataSource.sort = this.sort;
    //this.dataSource._paginator = this.paginator;
    this.table.dataSource = this.dataSource;
    //console.log("<< ngAfterViewInit: ", this.dataSource?.filteredData);
  }

  ngOnInit(): void {
    //console.log(">> ngOnInit", this.paginator);
    //this.loadData();
    //console.log("<< ngOnInit: " + this.dataSource?.filteredData);
  }

  openAddDialog() {
    const dialogRef = this.dialogService.open(AddDialogComponent, {
      data: { item: {} },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        // After dialog is closed we're doing frontend updates
        // For add we're just pushing a new row inside DataService
        this.itemCategoryService.itemCategoryChange.value.push(
          this.itemCategoryService.getDialogData()
        );
        this.refreshTable();
      }
    });
  }

  viewItem(row: ItemsItem)
  {
    this.selectedItemId = row.ItemId + "";
    //console.log("View Item: ", row);
    const dialogRef = this.dialogService.open(DetailDialogComponent, {
      data: {
        ItemId: row.ItemId,
        ItemName: row.ItemName,
        ItemDescription: row.ItemDescription,
        Uom: row.Uom,
        Brand: row.Brand,
        Category: row.Category,
        HsnCode: row.HsnCode,
        CatalogueNumber: row.CatalogueNumber,
        UnitCost: row.UnitCost,
        CurrencyCode: row.CurrencyCode,
        CreatedBy: row.CreatedBy,
        CreatedTimestamp: row.CreatedTimestamp,
        UpdatedBy: row.UpdatedBy,
        UpdatedTimestamp: row.UpdatedTimestamp
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      //console.log("--startEdit:2 ");
      if (result === 1) {
        // When using an edit things are little different, firstly we find record inside DataService by id
        const foundIndex = this.itemCategoryService.itemCategoryChange.value.findIndex(
          (x) => x.CategoryCode === this.selectedItemId
        );
        //console.log("Index Value: " + foundIndex);
        // Then you update that record using data from dialogData (values you enetered)
        this.itemCategoryService.itemCategoryChange.value[foundIndex] =
          this.itemCategoryService.getDialogData();
        // And lastly refresh table
        //console.log("Item dailogData before refreshTable!", this.itemCategoryService.getDialogData());
        //console.log("Item List before refreshTable!", this.itemCategoryService.items);
        this.refreshTable();
      }
    });
  }

  startEdit(row: ItemsItem)
  {
    this.selectedItemId = row.ItemId + "";
    //console.log("Editing item: ", row);
    ////console.log("--startEdit1: ", this.itemCategoryService.items);
    const dialogRef = this.dialogService.open(UpdateDialogComponent, {
      data: {
        ItemId: row.ItemId,
        ItemName: row.ItemName,
        ItemDescription: row.ItemDescription,
        Uom: row.Uom,
        Brand: row.Brand,
        Category: row.Category,
        HsnCode: row.HsnCode,
        CatalogueNumber: row.CatalogueNumber,
        UnitCost: row.UnitCost,
        CurrencyCode: row.CurrencyCode,
        CreatedBy: row.CreatedBy,
        CreatedTimestamp: row.CreatedTimestamp,
        UpdatedBy: row.UpdatedBy,
        UpdatedTimestamp: row.UpdatedTimestamp
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      //console.log("--startEdit:2 ");
      if (result === 1) {
        // When using an edit things are little different, firstly we find record inside DataService by id
        const foundIndex = this.itemCategoryService.itemCategoryChange.value.findIndex(
          (x) => x.CategoryCode === this.selectedItemId
        );
        //console.log("Index Value: " + foundIndex);
        // Then you update that record using data from dialogData (values you enetered)
        this.itemCategoryService.itemCategoryChange.value[foundIndex] =
          this.itemCategoryService.getDialogData();
        // And lastly refresh table
        //console.log("Item dailogData before refreshTable!", this.itemCategoryService.getDialogData());
        //console.log("Item List before refreshTable!", this.itemCategoryService.items);
        this.refreshTable();
      }
    });
  }

  deleteItem(item: ItemsItem) {
    this.selectedItemId = item.ItemId + "";
    const dialogRef = this.dialogService.open(DeleteDialogComponent, {
      data: { 
        itemId: item.ItemId, 
        itemName:item.ItemName,
        itemDescription: item.ItemDescription
       }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        const foundIndex = this.itemCategoryService.itemCategoryChange.value.findIndex(
          (x) => x.CategoryCode === this.selectedItemId
        );
        //console.log("Index Value: ", foundIndex);
        // for delete we use splice in order to remove single object from DataService
        this.itemCategoryService.itemCategoryChange.value.splice(foundIndex, 1);
        this.refreshTable();
      }
    });
  }

  exportAsExcel(){
    //console.log("Table Data Exporting!!");
    const exportableData = this.dataSource.filteredData.map(itemCategory => ({
        CategoryCode: itemCategory.CategoryCode,
        CategoryName: itemCategory.CategoryName,
    }));
    const workSheet = XLSX.utils.json_to_sheet(exportableData);
    XLSX.utils.sheet_add_aoa(workSheet, [this.exportableColumns], { origin: "A1" });
    const workBook: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workBook, workSheet, 'Item Categories');
    XLSX.writeFile(workBook, 'ItemCategories.xlsx');
    //console.log("Table Data Exported!!");
  }

  private refreshTable() {
    //console.log("--refreshTable:1 ");
    this.paginator._changePageSize(this.paginator.pageSize);
    //console.log("--refreshTable:2 ");
  }

  

  onChange(event: any) {
    
    const target: DataTransfer = <DataTransfer>(event.target);
    this.isExcelFile = !!target.files[0].name.match(/(.xls|.xlsx)/);
    if (target.files.length > 1) {
      this.inputFile.nativeElement.value = '';
    }
    if (this.isExcelFile) {
      this.spinnerEnabled = true;
      const reader: FileReader = new FileReader();
      reader.onload = (e: any) => {
        /* read workbook */
        const bstr: string = e.target.result;
        const wb: XLSX.WorkBook = XLSX.read(bstr, { type: 'binary' });

        /* grab first sheet */
        const wsname: string = wb.SheetNames[0];
        const ws: XLSX.WorkSheet = wb.Sheets[wsname];

        /* save data */
        
        this.importedData = XLSX.utils.sheet_to_json(ws);
        //console.log("No of imported rows: ", this.importedData.length);
        this.importedData.forEach(imported => {
          this.itemCategoryService.itemCategories.push(imported);
          this.itemCategoryService.addItemCategory(imported);
        });
        
      };

      reader.readAsBinaryString(target.files[0]);

      reader.onloadend = (e) => {
        this.spinnerEnabled = false;
        this.dataSheet.next(this.importedData);
      }
    } else {
      this.inputFile.nativeElement.value = '';
    }
  }

}
