import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WriteRevisionComponent } from './write-revision.component';

describe('WriteRevisionComponent', () => {
  let component: WriteRevisionComponent;
  let fixture: ComponentFixture<WriteRevisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WriteRevisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WriteRevisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
