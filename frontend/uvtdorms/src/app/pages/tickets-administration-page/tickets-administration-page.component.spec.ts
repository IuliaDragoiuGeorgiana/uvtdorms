import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketsAdministrationPageComponent } from './tickets-administration-page.component';

describe('TicketsAdministrationPageComponent', () => {
  let component: TicketsAdministrationPageComponent;
  let fixture: ComponentFixture<TicketsAdministrationPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TicketsAdministrationPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TicketsAdministrationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
