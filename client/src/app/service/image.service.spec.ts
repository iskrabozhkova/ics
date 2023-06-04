import { TestBed } from '@angular/core/testing';
import { ImageService } from './image.service';
import { Image } from '../interfaces/image';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';

describe('ImageService', () => {
  let service: ImageService;

  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, HttpClientModule],
      providers: [ImageService],
    });
    service = TestBed.inject(ImageService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getImages', () => {
    it('should return images', () => {
      const imageResponse: Image[] = [
        { url: 'http:/imageUrl', analysisService: 'Imagga', uploadedAt: '10-07-2015', width: 200, height: 300 },
        { url: 'http:/testImageUrl', analysisService: 'Imagga', uploadedAt: '10-07-2020', width: 400, height: 400 }
      ];

      service.getImages().subscribe((images: Image[]) => {
        expect(images).toEqual(imageResponse);
      });
      const req = httpMock.expectOne(service.getApiUrl());
      expect(req.request.method).toBe('GET');
      req.flush(imageResponse);
    });
  });
  // describe('getImageById', () => {
  //   it('should return an image for a given id', () => {
  //     const imageId : number = 1;
  //     const imageResponse: Image = {
  //       imageId: imageId,
  //       url: 'http:/imageUrl',
  //       analysisService: 'Imagga',
  //       uploadedAt: '10-07-2015',
  //       width: 200,
  //       height: 300,
  //     };

  //     service.getImageById(imageId).subscribe((image: Image) => {
  //       expect(image).toEqual(imageResponse);
  //     });

  //     const req = httpMock.expectOne(`${service.getApiUrl()}/${imageId}`);
  //     expect(req.request.method).toBe('GET');
  //     req.flush(imageResponse);
  //   });
  // });
});
