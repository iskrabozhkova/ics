import { NgModule } from '@angular/core';
// eslint-disable-next-line import/no-extraneous-dependencies
import { StoreModule } from '@ngrx/store';
// import { simpleReducer } from './simple.reducer';
import { ClarityModule } from '@clr/angular';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { ImageUploadComponent } from './image-upload/image-upload.component';
import { MenuComponent } from './menu/menu.component';
import { GalleryComponent } from './gallery/gallery.component';
import { ImageDetailsComponent } from './image-details/image-details.component';
import { imageReducer } from './state/image.reducer';
import { SearchComponent } from './search/search.component';
import { ErrorPageComponent } from './error-page/error-page.component';
// eslint-disable-next-line import/extensions

@NgModule({
  declarations: [
    AppComponent,
    ImageUploadComponent,
    MenuComponent,
    GalleryComponent,
    ImageDetailsComponent,
    SearchComponent,
    ErrorPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ClarityModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    StoreModule.forRoot({ message: imageReducer }),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
