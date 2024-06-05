import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvenimentsPageComponent } from './eveniments-page.component';

describe('EvenimentsPageComponent', () => {
  let component: EvenimentsPageComponent;
  let fixture: ComponentFixture<EvenimentsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EvenimentsPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EvenimentsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
