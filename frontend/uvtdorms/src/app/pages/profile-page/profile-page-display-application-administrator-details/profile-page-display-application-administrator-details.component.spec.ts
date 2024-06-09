import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilePageDisplayApplicationAdministratorDetailsComponent } from './profile-page-display-application-administrator-details.component';

describe('ProfilePageDisplayApplicationAdministratorDetailsComponent', () => {
  let component: ProfilePageDisplayApplicationAdministratorDetailsComponent;
  let fixture: ComponentFixture<ProfilePageDisplayApplicationAdministratorDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfilePageDisplayApplicationAdministratorDetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfilePageDisplayApplicationAdministratorDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
