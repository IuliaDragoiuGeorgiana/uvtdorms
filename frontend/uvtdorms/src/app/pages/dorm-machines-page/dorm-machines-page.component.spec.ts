import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DormMachinesPageComponent } from './dorm-machines-page.component';

describe('DormMachinesPageComponent', () => {
  let component: DormMachinesPageComponent;
  let fixture: ComponentFixture<DormMachinesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DormMachinesPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DormMachinesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
