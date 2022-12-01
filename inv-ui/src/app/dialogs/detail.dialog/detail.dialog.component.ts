import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ItemsItem } from 'src/app/models/item';
import { ItemService } from 'src/app/providers/item.service';

@Component({
  selector: 'app-detail.dialog',
  templateUrl: './detail.dialog.component.html',
  styleUrls: ['./detail.dialog.component.css']
})
export class DetailDialogComponent {
  constructor(public dialogRef: MatDialogRef<DetailDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: ItemsItem,
              public itemService: ItemService) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  public editItem(): void {
    console.log("Edit Item: ", this.data);
    //this.itemService.addItem(this.data);
  }
}



