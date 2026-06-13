import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListLayout } from './list-layout';

describe('ListLayout', () => {
  let component: ListLayout;
  let fixture: ComponentFixture<ListLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListLayout],
    }).compileComponents();

    fixture = TestBed.createComponent(ListLayout);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
