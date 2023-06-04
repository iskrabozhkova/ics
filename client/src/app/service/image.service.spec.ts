import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';
import { of } from 'rxjs';
import { Image } from '../interfaces/image';
import { ImageService } from './image.service';

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
        { url: 'http:/testImageUrl', analysisService: 'Imagga', uploadedAt: '10-07-2020', width: 400, height: 400 },
      ];

      service.getImages().subscribe((images: Image[]) => {
        expect(images).toEqual(imageResponse);
      });
      const req = httpMock.expectOne(service.getApiUrl());
      expect(req.request.method).toBe('GET');
      req.flush(imageResponse);
    });
  });
  describe('getImageById', () => {
    it('should return a image by id', (done) => {
      const userResponse = {
        imageId: 5, url: 'http:/imageUrl', analysisService: 'Imagga', uploadedAt: '10-07-2015', width: 200, height: 300,
      };
      let response;
      spyOn(service, 'getImageById').and.returnValue(of(userResponse));
  
      service.getImageById('5').subscribe(res => {
        response = res;
        expect(response).toEqual(userResponse); 
        done();
      });
    });
  });
  describe('deleteImageById', () => {
    it('should delete an image by id', (done) => {
      const imageId = 5;
      const response = 'Image is deleted successfully';
      spyOn(service, 'deleteImage').and.returnValue(of(response));
  
      service.deleteImage(imageId).subscribe(res => {
        expect(res).toEqual(response); 
        done();
      });
    });
  });
  describe('searchByLabels', () => {
    it('should return images by given labels', () => {
      const labels = 'sun';
      const apiUrl = 'http://localhost:8080/api/images';
      const url = `${ apiUrl }?labels=${ encodeURIComponent(labels) }`;
  
      const imageResponse: Image[] = [
        { url: 'http:/imageUrl1', analysisService: 'Imagga', uploadedAt: '10-07-2015', width: 200, height: 300 },
        { url: 'http:/imageUrl2', analysisService: 'Imagga', uploadedAt: '10-07-2020', width: 400, height: 400 },
      ];
  
      spyOn(service, 'getApiUrl').and.returnValue(apiUrl);
  
      service.searchByLabels(labels).subscribe((images: Image[]) => {
        expect(images).toEqual(imageResponse);
      });
  
      const req = httpMock.expectOne(url);
      expect(req.request.method).toBe('GET');
      req.flush(imageResponse);
    }); 
  });
});