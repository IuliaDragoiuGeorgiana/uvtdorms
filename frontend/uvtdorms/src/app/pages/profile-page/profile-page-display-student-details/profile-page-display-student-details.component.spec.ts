import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilePageDisplayStudentDetailsComponent } from './profile-page-display-student-details.component';

describe('ProfilePageDisplayStudentDetailsComponent', () => {
  let component: ProfilePageDisplayStudentDetailsComponent;
  let fixture: ComponentFixture<ProfilePageDisplayStudentDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfilePageDisplayStudentDetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfilePageDisplayStudentDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
