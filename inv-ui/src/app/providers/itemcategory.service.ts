import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { LoginService } from './login.service';
import { ItemCategory } from '../models/itemCategory';


@Injectable({
  providedIn: 'root'
})
export class ItemCategoryService {

  isJson: boolean = true;

  itemCategoryUrl = '/inventory/service/getItemCategories';
  saveUrl = 'inventory/service/itemCategory/saveItemCategory';
  deleteUrl = 'inventory/service/itemCategory/deleteByItemCategoryId';

  itemCategoryChange: BehaviorSubject<ItemCategory[]> = new BehaviorSubject<ItemCategory[]>([]);
  
  // Temporarily stores data from dialogs
  dialogData: any;
  totalCount !: number;

  constructor(private httpClient: HttpClient,
              private loginService: LoginService) {
                if(this.isJson){
                  this.itemCategoryUrl = '/assets/categoryFromBackend.json';
                }            
              }

  get itemCategories(): ItemCategory[] {
    return this.itemCategoryChange.value;
  }

  getDialogData() {
    return this.dialogData;
  }

  getAllItemCategories(): void {
    console.log(">> getAllItemCategories");
    this.httpClient.get<ItemCategory[]>(this.itemCategoryUrl).subscribe((data: ItemCategory[]) => {
          data.forEach((itemCategory: ItemCategory) => {
            this.itemCategories.push(itemCategory);
          });
        console.log("-- getAllItemCategories: No of records: ", data);
      },
      (error: HttpErrorResponse) => {
        console.log (error.name + ' ' + error.message);
      });
      console.log("<< getAllItemCategories");
  }

  addItemCategory(itemCategory: ItemCategory): void 
  {
    if(this.isJson){
      itemCategory.CreatedBy = this.loginService.loggedUser;
      this.addItemCategoryJson(itemCategory);
      return;
    }
    
    itemCategory.CreatedBy = this.loginService.loggedUser;
    this.httpClient.post(this.saveUrl, itemCategory).subscribe(data => {
      this.dialogData = data;
      console.log("ItemCategory Successfully Added!", data);
      //this.toasterService.showToaster('Successfully added', 3000);
      },
      (err: HttpErrorResponse) => {
        console.log('Error occurred. Details: ' + err.name + ' ' + err.message, 8000);
      //this.toasterService.showToaster('Error occurred. Details: ' + err.name + ' ' + err.message, 8000);
    });
    
  }

  updateItemCategory(itemCategory: ItemCategory): void {
    console.log("updating Item Category!", itemCategory);
    if(this.isJson){
      itemCategory.UpdatedBy = this.loginService.loggedUser;
      console.log("Item Category List before update!", this.itemCategories);
      this.updateItemCategoryJson(itemCategory);
      return;
    }
    
    itemCategory.UpdatedBy = this.loginService.loggedUser;
    console.log("Item Category List before update!", this.itemCategories);
    this.httpClient.post(this.saveUrl, itemCategory).subscribe(data => {
      this.dialogData = data;
      console.log("Item Successfully Updated!", data);
      },
      (err: HttpErrorResponse) => {
        console.log('Error occurred. Details: Name: ', err.name,  ' Message: ', err.message, 8000);
    });
    console.log("Item Category Successfully Updated1!", itemCategory);
    console.log("Item Category List after update!", this.itemCategories);
  }

  deleteItemCategory(itemCategoryId: string): void {
    console.log("Deleting itemCategory: ", itemCategoryId);
    this.httpClient.delete(this.deleteUrl + itemCategoryId).subscribe(data => {
      console.log("Item Category Successfully Deleted!: ", data);
      },
      (err: HttpErrorResponse) => {
        console.log('Error occurred. Details: Name: ', err.name,  ' Message: ', err.message, 8000);
      }
    );
  }

  addItemCategoryJson(itemCategory: ItemCategory){
    this.dialogData = itemCategory;
    console.log("Item Successfully Added!", itemCategory);
  }

  updateItemCategoryJson(itemCategory: ItemCategory){
    this.dialogData = itemCategory;
    console.log("Item Successfully Updated!", itemCategory);
  }
  
}
