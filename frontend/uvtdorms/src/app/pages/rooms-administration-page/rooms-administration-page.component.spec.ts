import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomsAdministrationPageComponent } from './rooms-administration-page.component';

describe('RoomsAdministrationPageComponent', () => {
  let component: RoomsAdministrationPageComponent;
  let fixture: ComponentFixture<RoomsAdministrationPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RoomsAdministrationPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RoomsAdministrationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
