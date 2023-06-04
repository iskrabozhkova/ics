import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';
import { Label } from '../interfaces/label';
import { LabelService } from './label.service';

describe('LabelService', () => {
  let service: LabelService;

  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, HttpClientModule],
      providers: [LabelService],
    });
    service = TestBed.inject(LabelService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getLabels', () => {
    it('should return labels', () => {
      const labelResponse: Label[] = [
        { labelId: 1, name: 'sun', confidence: 100 },
        { labelId: 2, name: 'tree', confidence: 98 },
      ];

      service.getLabels().subscribe((lables: Label[]) => {
        expect(lables).toEqual(labelResponse);
      });
      const req = httpMock.expectOne(service.getApiUrl());
      expect(req.request.method).toBe('GET');
      req.flush(labelResponse);
    });
  });
});
