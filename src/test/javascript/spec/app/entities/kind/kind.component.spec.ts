import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VtmgTestModule } from '../../../test.module';
import { KindComponent } from 'app/entities/kind/kind.component';
import { KindService } from 'app/entities/kind/kind.service';
import { Kind } from 'app/shared/model/kind.model';

describe('Component Tests', () => {
  describe('Kind Management Component', () => {
    let comp: KindComponent;
    let fixture: ComponentFixture<KindComponent>;
    let service: KindService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VtmgTestModule],
        declarations: [KindComponent]
      })
        .overrideTemplate(KindComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KindComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KindService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Kind('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.kinds && comp.kinds[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
