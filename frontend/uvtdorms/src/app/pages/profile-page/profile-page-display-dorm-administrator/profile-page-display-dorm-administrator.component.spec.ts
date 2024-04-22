import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilePageDisplayDormAdministratorComponent } from './profile-page-display-dorm-administrator.component';

describe('ProfilePageDisplayDormAdministratorComponent', () => {
  let component: ProfilePageDisplayDormAdministratorComponent;
  let fixture: ComponentFixture<ProfilePageDisplayDormAdministratorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfilePageDisplayDormAdministratorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfilePageDisplayDormAdministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
