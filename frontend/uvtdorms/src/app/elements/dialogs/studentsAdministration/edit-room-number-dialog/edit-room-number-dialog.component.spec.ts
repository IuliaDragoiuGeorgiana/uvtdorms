import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditRoomNumberDialogComponent } from './edit-room-number-dialog.component';

describe('EditRoomNumberDialogComponent', () => {
  let component: EditRoomNumberDialogComponent;
  let fixture: ComponentFixture<EditRoomNumberDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditRoomNumberDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditRoomNumberDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
