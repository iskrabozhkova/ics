import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ImageService } from '../service/image.service';
import { Image } from '../interfaces/image';
// eslint-disable-next-line import/no-extraneous-dependencies
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs/internal/Observable';

@Component({
  selector: 'ics-image-details',
  templateUrl: './image-details.component.html',
  styleUrls: ['./image-details.component.scss'],
})
export class ImageDetailsComponent implements OnInit {
  image$: Observable<Image> | null = null;

  constructor(
    private imageService: ImageService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const imageId = this.route.snapshot.paramMap.get('id');

    if (imageId) {
      this.image$ = this.imageService.getImageById(imageId);
    }
  }
}
