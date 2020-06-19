import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OnRevisePapersComponent } from './on-revise-papers.component';

describe('OnRevisePapersComponent', () => {
  let component: OnRevisePapersComponent;
  let fixture: ComponentFixture<OnRevisePapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OnRevisePapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OnRevisePapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
