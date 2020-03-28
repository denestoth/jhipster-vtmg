import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VtmgTestModule } from '../../../test.module';
import { KindDetailComponent } from 'app/entities/kind/kind-detail.component';
import { Kind } from 'app/shared/model/kind.model';

describe('Component Tests', () => {
  describe('Kind Management Detail Component', () => {
    let comp: KindDetailComponent;
    let fixture: ComponentFixture<KindDetailComponent>;
    const route = ({ data: of({ kind: new Kind('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VtmgTestModule],
        declarations: [KindDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(KindDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KindDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load kind on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.kind).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
