import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationReviewerComponent } from './registration-reviewer.component';

describe('RegistrationReviewerComponent', () => {
  let component: RegistrationReviewerComponent;
  let fixture: ComponentFixture<RegistrationReviewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistrationReviewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationReviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
