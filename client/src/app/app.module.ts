import { NgModule } from '@angular/core';
// eslint-disable-next-line import/no-extraneous-dependencies
import { StoreModule } from '@ngrx/store';
// import { simpleReducer } from './simple.reducer';
import { ClarityModule } from '@clr/angular';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ImageUploadComponent } from './image-upload/image-upload.component';
import { MenuComponent } from './menu/menu.component';
import { GalleryComponent } from './gallery/gallery.component';
import { ImageDetailsComponent } from './image-details/image-details.component';
import { imageReducer } from './state/image.reducer';
import { SearchComponent } from './search/search.component';


@NgModule({
  declarations: [
    AppComponent,
    ImageUploadComponent,
    MenuComponent,
    GalleryComponent,
    ImageDetailsComponent,
    SearchComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ClarityModule,
    FormsModule,
    ReactiveFormsModule,
    StoreModule.forRoot({ message: imageReducer }),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
