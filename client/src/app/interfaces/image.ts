import { Label } from './label';

export interface Image {
  imageId?: number;
  url: string | null;
  analysisService: string;
  uploadedAt: string;
  width: number;
  height: number;
  labels?: Label[];
}

