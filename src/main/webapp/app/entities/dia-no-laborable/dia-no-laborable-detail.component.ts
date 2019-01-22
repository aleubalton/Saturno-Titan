import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiaNoLaborable } from 'app/shared/model/dia-no-laborable.model';

@Component({
    selector: 'jhi-dia-no-laborable-detail',
    templateUrl: './dia-no-laborable-detail.component.html'
})
export class DiaNoLaborableDetailComponent implements OnInit {
    diaNoLaborable: IDiaNoLaborable;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ diaNoLaborable }) => {
            this.diaNoLaborable = diaNoLaborable;
        });
    }

    previousState() {
        window.history.back();
    }
}
