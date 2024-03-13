import { TestBed } from '@angular/core/testing';

import { NeedCacheService } from './need-cache.service';

describe('NeedCacheService', () => {
  let service: NeedCacheService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NeedCacheService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
