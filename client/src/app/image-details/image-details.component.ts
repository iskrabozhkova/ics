import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ImageService } from '../service/image.service';
import { Image } from '../interfaces/image';
//import { Subscription } from 'rxjs';
// eslint-disable-next-line import/no-extraneous-dependencies
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs/internal/Observable';
// eslint-disable-next-line import/no-extraneous-dependencies

//import { selectImageId } from '../state/image.selector';

@Component({
  selector: 'ics-image-details',
  templateUrl: './image-details.component.html',
  styleUrls: ['./image-details.component.scss'],
})
export class ImageDetailsComponent implements OnInit {
  constructor(private imageService: ImageService, private route: ActivatedRoute, private store : Store) {}

  image$: Observable<Image> | null = null;

  // imageSubscription: Subscription = new Subscription();

  // imageId: string | null = '';

  ngOnInit(): void {
    // this.imageSubscription = this.store.select(selectImageId).subscribe((id) => {
    //   this.imageId = id;
    //   console.log('Image ID:', id);
    // });
    const imageId = this.route.snapshot.paramMap.get('id');
    
    if (imageId) {
      this.image$ = this.imageService.getImageById(imageId);
    }
  }

}
