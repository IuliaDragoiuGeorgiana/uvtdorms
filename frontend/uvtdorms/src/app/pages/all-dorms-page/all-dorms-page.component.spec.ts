import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllDormsPageComponent } from './all-dorms-page.component';

describe('AllDormsPageComponent', () => {
  let component: AllDormsPageComponent;
  let fixture: ComponentFixture<AllDormsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AllDormsPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AllDormsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
