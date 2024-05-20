import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeForgottenPasswordPageComponent } from './change-forgotten-password-page.component';

describe('ChangeForgottenPasswordPageComponent', () => {
  let component: ChangeForgottenPasswordPageComponent;
  let fixture: ComponentFixture<ChangeForgottenPasswordPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ChangeForgottenPasswordPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ChangeForgottenPasswordPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
