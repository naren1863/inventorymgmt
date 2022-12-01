import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/ng-material.module';
import { AppComponent } from './app.component';
import { MatSidenav, MatSidenavModule } from '@angular/material/sidenav';
import { NavMenuComponent } from './nav-menu/nav-menu.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { ItemsComponent } from './items/items.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { HttpClientModule } from '@angular/common/http';

import { AddDialogComponent } from './dialogs/add.dialog/add.dialog.component';
import { ItemService } from './providers/item.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { UpdateDialogComponent } from './dialogs/update.dialog/update.dialog.component';
import { DeleteDialogComponent } from './dialogs/delete.dialog/delete.dialog.component';
import { DetailDialogComponent } from './dialogs/detail.dialog/detail.dialog.component';
import { ItemCategoryComponent } from './itemcategory/itemcategory.component';
import { ItemCategoryService } from './providers/itemcategory.service';


@NgModule({
  declarations: [
    AppComponent,
    NavMenuComponent,
    ItemsComponent,
    AddDialogComponent,
    UpdateDialogComponent,
    DeleteDialogComponent,
    DetailDialogComponent,
    ItemCategoryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    MaterialModule,
    MatSidenavModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatInputModule,
    MatListModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatIconModule
    
  ],
  providers: [
    ItemService,
    ItemCategoryService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
