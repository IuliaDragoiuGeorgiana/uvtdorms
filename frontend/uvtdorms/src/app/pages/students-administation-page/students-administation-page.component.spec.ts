import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentsAdministationPageComponent } from './students-administation-page.component';

describe('StudentsAdministationPageComponent', () => {
  let component: StudentsAdministationPageComponent;
  let fixture: ComponentFixture<StudentsAdministationPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StudentsAdministationPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StudentsAdministationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
