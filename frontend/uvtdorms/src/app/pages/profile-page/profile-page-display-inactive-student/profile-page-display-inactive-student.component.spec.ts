import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilePageDisplayInactiveStudentComponent } from './profile-page-display-inactive-student.component';

describe('ProfilePageDisplayInactiveStudentComponent', () => {
  let component: ProfilePageDisplayInactiveStudentComponent;
  let fixture: ComponentFixture<ProfilePageDisplayInactiveStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfilePageDisplayInactiveStudentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfilePageDisplayInactiveStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
