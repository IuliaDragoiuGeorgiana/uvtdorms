import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewDormDialogComponent } from './add-new-dorm-dialog.component';

describe('AddNewDormDialogComponent', () => {
  let component: AddNewDormDialogComponent;
  let fixture: ComponentFixture<AddNewDormDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddNewDormDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddNewDormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
