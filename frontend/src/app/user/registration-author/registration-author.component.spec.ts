import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationAuthorComponent } from './registration-author.component';

describe('RegistrationAuthorComponent', () => {
  let component: RegistrationAuthorComponent;
  let fixture: ComponentFixture<RegistrationAuthorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistrationAuthorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
