import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewerPapersComponent } from './reviewer-papers.component';

describe('ReviewerPapersComponent', () => {
  let component: ReviewerPapersComponent;
  let fixture: ComponentFixture<ReviewerPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewerPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewerPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
