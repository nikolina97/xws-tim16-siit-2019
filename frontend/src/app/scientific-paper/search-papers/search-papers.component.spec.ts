import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchPapersComponent } from './search-papers.component';

describe('SearchPapersComponent', () => {
  let component: SearchPapersComponent;
  let fixture: ComponentFixture<SearchPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
