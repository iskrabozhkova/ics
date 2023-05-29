// eslint-disable-next-line import/no-extraneous-dependencies
import { createAction, props } from '@ngrx/store';

export const setImageId = createAction(
  'Image id',
  props<{ id: string }>(),
);
