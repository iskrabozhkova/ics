/* eslint-disable @typescript-eslint/no-shadow */
import { Component, OnInit } from '@angular/core';
//import { NavigationEnd, Router } from '@angular/router';import { Subscription } from 'rxjs';
// eslint-disable-next-line import/no-extraneous-dependencies
import { Store } from '@ngrx/store';
import { Observable, Subscription } from 'rxjs';
import { selectImageId } from '../state/image.selector';
import { ImageState } from '../state/image.state';

interface AppState {
  message: ImageState;
}

@Component({
  selector: 'ics-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent {
  imageSubscription: Subscription = new Subscription();

  imageId: string = '';

  constructor(private store : Store<AppState>) {
    this.store.select('message').subscribe(res => {
      this.imageId = `/images/${ res.id }`;

      console.log(this.imageId);
    });
  }

}
