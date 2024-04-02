import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { NeedEditComponent } from './need-edit.compononet';

describe('NeedEditComponent', () => {
  let component: NeedEditComponent;
  let fixture: ComponentFixture<NeedEditComponent>;
  let needService: NeedService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NeedEditComponent],
      providers: [NeedService] 
    })
    .compileComponents();

    fixture = TestBed.createComponent(NeedEditComponent);
    component = fixture.componentInstance;
    needService = TestBed.inject(NeedService); 
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
