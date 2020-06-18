import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PapersFromAuthorComponent } from './papers-from-author.component';

describe('PapersFromAuthorComponent', () => {
  let component: PapersFromAuthorComponent;
  let fixture: ComponentFixture<PapersFromAuthorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PapersFromAuthorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PapersFromAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
