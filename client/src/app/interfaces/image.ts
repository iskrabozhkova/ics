import { Label } from './label';

export interface Image {
  imageId?: number;
  url: string | null;
  analysisService: string;
  date: string;
  width: number;
  height: number;
  labels?: Label[];
}

