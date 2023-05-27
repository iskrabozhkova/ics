import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ImageService } from '../service/image.service';
import { Image } from '../interfaces/image';

@Component({
  selector: 'ics-image-details',
  templateUrl: './image-details.component.html',
  styleUrls: ['./image-details.component.scss'],
})
export class ImageDetailsComponent implements OnInit {
  constructor(private imageService: ImageService, private route: ActivatedRoute) {}

  image! : Image;

  ngOnInit(): void {
    const imageId = this.route.snapshot.paramMap.get('id');
    if (imageId) {
      this.imageService.getImageById(imageId).subscribe(res => this.image = res);
    }
  }

}
