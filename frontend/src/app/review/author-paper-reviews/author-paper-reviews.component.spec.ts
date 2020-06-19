import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorPaperReviewsComponent } from './author-paper-reviews.component';

describe('AuthorPaperReviewsComponent', () => {
  let component: AuthorPaperReviewsComponent;
  let fixture: ComponentFixture<AuthorPaperReviewsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthorPaperReviewsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorPaperReviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
