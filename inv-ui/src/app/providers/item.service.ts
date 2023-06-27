import { Injectable } from '@angular/core';
import { ItemsItem } from '../models/item';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { LoginService } from './login.service';
import { Uom } from '../models/uom';
import { ItemCategory } from '../models/itemCategory';
import { Brand } from '../models/brand';
import { HsnCode } from '../models/hsnCode';
import { ItemCategoryService } from './itemcategory.service';
import { GlobalConfig } from './globalConfig.service';
//import CurrencyList from 'currency-list';


@Injectable({
  providedIn: 'root'
})
export class ItemService {
  static getAllItemUoms() {
    throw new Error('Method not implemented.');
  }

  configUrl = '/inventory/service/getItems';
  saveUrl = 'inventory/service/item/saveItem';
  deleteUrl = 'inventory/service/item/deleteByItemId/';
  allUomsUrl = '/inventory/service/getUoms';
  itemCategoryUrl = '/inventory/service/getItemCategories';
  brandUrl = '/inventory/service/getBrands';
  hsnCodeUrl = '/inventory/service/getHsnCodes';

  public itemUnits: Uom[] = [];
  //public itemCategories: ItemCategory[] = [];
  public brands: Brand[] = [];
  public hsnCodes: HsnCode[] = [];
  public currencies: string[] = ['INR', 'USD', 'AUD'];

  itemChange: BehaviorSubject<ItemsItem[]> = new BehaviorSubject<ItemsItem[]>([]);
  // Temporarily stores data from dialogs
  dialogData: any;
  totalCount !: number;

  constructor(private httpClient: HttpClient,
              private loginService: LoginService,
              private itemCategoryService: ItemCategoryService, 
              private globalConfig: GlobalConfig) {
                console.log("-- globalConfig.isJson: ", globalConfig.isJson);
                if(globalConfig.isJson){
                  console.log("-- globalConfig.isJson is TRUE!!");
                  this.configUrl = '/assets/itemsFromBackend.json';
                  this.allUomsUrl = '/assets/uomsFromBackend.json';
                  this.itemCategoryUrl = '/assets/categoryFromBackend.json';
                  this.brandUrl = '/assets/brandFromBackend.json';
                  this.hsnCodeUrl = '/assets/hsnCodeFromBackend.json';
                }
                this.getAllItemUoms();
                this.getBrands();
                //this.getItemCategories();
                this.getHsnCodes();
              }

  get items(): ItemsItem[] {
    return this.itemChange.value;
  }

  getDialogData() {
    return this.dialogData;
  }

  getAllItemUoms(): void {
    //console.log(">> getAllItemUoms");
    this.httpClient.get<Uom[]>(this.allUomsUrl).subscribe((data: Uom[]) => {
          data.forEach((uom: Uom) => {
            this.itemUnits.push(uom);
          });
      //  console.log("-- getAllItemUoms: No of records: ", data.length);
      },
      (error: HttpErrorResponse) => {
        console.log (error.name + ' ' + error.message);
      });
      //console.log("<< getAllItems");
  }

   getItemCategories(): ItemCategory[] {return []}
  // getItemCategories(): ItemCategory[] {
  //   return this.itemCategoryService.itemCategoryChange.value;
    // console.log(">> getItemCategories");
    // this.httpClient.get<ItemCategory[]>(this.itemCategoryUrl).subscribe((data: ItemCategory[]) => {
    //       data.forEach((itemCategory: ItemCategory) => {
    //         this.itemCategories.push(itemCategory);
    //       });
    //     console.log("-- getItemCategories: No of records: ", data);
    //   },
    //   (error: HttpErrorResponse) => {
    //     console.log (error.name + ' ' + error.message);
    //   });
    //   console.log("<< getItemCategories");
  //}

  getBrands(): void {
    //console.log(">> getBrands");
    this.httpClient.get<Brand[]>(this.brandUrl).subscribe((data: Brand[]) => {
          data.forEach((brand: Brand) => {
            this.brands.push(brand);
          });
        //console.log("-- getBrands: No of records: ", data.length);
      },
      (error: HttpErrorResponse) => {
        console.log (error.name + ' ' + error.message);
      });
      //console.log("<< getBrands");
  }

  getHsnCodes(): void {
    //console.log(">> getHsnCodes");
    this.httpClient.get<HsnCode[]>(this.hsnCodeUrl).subscribe((data: HsnCode[]) => {
          data.forEach((hsnCode: HsnCode) => {
            this.hsnCodes.push(hsnCode);
          });
        //console.log("-- getHsnCodes: No of records: ", data.length);
      },
      (error: HttpErrorResponse) => {
        console.log (error.name + ' ' + error.message);
      });
      //console.log("<< getHsnCodes");
  }

  getAllItems(pageNo: number, pageSize: number): void {
    console.log(">> getAllItems: ", pageNo, pageSize, this.configUrl);
    this.httpClient.get<ItemsItem[]>(this.configUrl+"?pageNo="+pageNo+"&pageSize="+pageSize).subscribe((response: any) => {
        this.itemChange.next(response.Data);
        this.totalCount = response.TotalCount;
        console.log("-- getAllItems: No of records: ", response.totalCount);
        console.log("-- this.totalCount: ", this.totalCount);
      },
      (error: HttpErrorResponse) => {
        console.log (error.name + ' ' + error.message);
      });
      console.log("<< getAllItems");
  }

  addItem(item: ItemsItem): void {

    item.CreatedBy = this.loginService.loggedUser;
    if(this.globalConfig.isJson){
      this.addItemJson(item);
      return;
    }

    this.httpClient.post(this.saveUrl, item).subscribe(data => {
      this.dialogData = data;
      console.log("Item Successfully Added!", data);
      //this.toasterService.showToaster('Successfully added', 3000);
      },
      (err: HttpErrorResponse) => {
        console.log('Error occurred. Details: ' + err.name + ' ' + err.message, 8000);
      //this.toasterService.showToaster('Error occurred. Details: ' + err.name + ' ' + err.message, 8000);
    });

  }

  updateItem(item: ItemsItem): void {
    console.log("Updating Item!", item, this.saveUrl);
    item.UpdatedBy = this.loginService.loggedUser;
    if(this.globalConfig.isJson){
      this.updateItemJson(item);
      return;
    }

    this.httpClient.post(this.saveUrl, item).subscribe(data => {
      this.dialogData = data;
      console.log("Backend Update: Item Successfully Updated!", data);
      },
      (err: HttpErrorResponse) => {
        console.log('Error occurred. Details: Name: ', err.name,  ' Message: ', err.message, 8000);
    });
  }

  deleteItem(itemId: string): void {
    console.log("Deleting Item: ", itemId);
    this.httpClient.delete(this.deleteUrl + itemId).subscribe(data => {
      console.log("Item Successfully Deleted!: ", data);
      },
      (err: HttpErrorResponse) => {
        console.log('Error occurred. Details: Name: ', err.name,  ' Message: ', err.message, 8000);
      }
    );
  }

  addItemJson(item: ItemsItem){
    this.dialogData = item;
    console.log("JSON Update: Item Successfully Added!", item);
  }

  updateItemJson(item: ItemsItem){
    this.dialogData = item;
    console.log("JSON Update: Item Successfully Updated!", item);
  }


/* REAL LIFE CRUD Methods I've used in projects. ToasterService uses Material Toasts for displaying messages:

    // ADD, POST METHOD
    addItem(kanbanItem: KanbanItem): void {
    this.httpClient.post(this.API_URL, kanbanItem).subscribe(data => {
      this.dialogData = kanbanItem;
      this.toasterService.showToaster('Successfully added', 3000);
      },
      (err: HttpErrorResponse) => {
      this.toasterService.showToaster('Error occurred. Details: ' + err.name + ' ' + err.message, 8000);
    });
   }

    // UPDATE, PUT METHOD
     updateItem(kanbanItem: KanbanItem): void {
    this.httpClient.put(this.API_URL + kanbanItem.id, kanbanItem).subscribe(data => {
        this.dialogData = kanbanItem;
        this.toasterService.showToaster('Successfully edited', 3000);
      },
      (err: HttpErrorResponse) => {
        this.toasterService.showToaster('Error occurred. Details: ' + err.name + ' ' + err.message, 8000);
      }
    );
  }

  // DELETE METHOD
  deleteItem(id: number): void {
    this.httpClient.delete(this.API_URL + id).subscribe(data => {
      console.log(data['']);
        this.toasterService.showToaster('Successfully deleted', 3000);
      },
      (err: HttpErrorResponse) => {
        this.toasterService.showToaster('Error occurred. Details: ' + err.name + ' ' + err.message, 8000);
      }
    );
  }
*/

}
