import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAdministrationPageComponent } from './user-administration-page.component';

describe('UserAdministrationPageComponent', () => {
  let component: UserAdministrationPageComponent;
  let fixture: ComponentFixture<UserAdministrationPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserAdministrationPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UserAdministrationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
