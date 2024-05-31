import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DormAdministratorsPageComponent } from './dorm-administrators-page.component';

describe('DormAdministratorsPageComponent', () => {
  let component: DormAdministratorsPageComponent;
  let fixture: ComponentFixture<DormAdministratorsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DormAdministratorsPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DormAdministratorsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
