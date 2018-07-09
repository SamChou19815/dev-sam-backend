import { Component, Inject, OnInit } from '@angular/core';
import { LoadingOverlayService } from '../../shared/overlay/loading-overlay.service';
import {
  PUSHED_CONTROLLER_DATA,
  PushedControllerOverlayRef
} from '../../shared/overlay/push-controller.service';
import { PushedControllerOverlayComponent } from '../../shared/overlay/pushed-controller-overlay.component';
import { FullAnalyzedArticle } from '../chunk-reader-data';
import { ChunkReaderNetworkService } from '../chunk-reader-network.service';

@Component({
  selector: 'app-chunk-reader-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: [
    '../../shared/overlay/pushed-controller-overlay.component.scss',
    './article-detail.component.css'
  ]
})
export class ArticleDetailComponent extends PushedControllerOverlayComponent implements OnInit {

  readonly articleDetail: FullAnalyzedArticle;

  constructor(ref: PushedControllerOverlayRef, @Inject(PUSHED_CONTROLLER_DATA) data: any,
              private chunkReaderNetworkService: ChunkReaderNetworkService,
              private loadingService: LoadingOverlayService) {
    super(ref);
    if (data == null) {
      throw new Error();
    }
    this.articleDetail = data;
  }

  ngOnInit() {
  }

  private async adjustSummary(limit: number) {
    const ref = this.loadingService.open();
    this.articleDetail.summaries = await this.chunkReaderNetworkService.adjustSummary(
      this.articleDetail.key, limit);
    ref.close();
  }

  less(): void {
    const summary = this.articleDetail.summaries;
    if (summary == null) {
      throw new Error();
    }
    const newLimit = summary.length - 1;
    this.adjustSummary(newLimit).then(() => {
    });
  }

  more(): void {
    const summary = this.articleDetail.summaries;
    if (summary == null) {
      throw new Error();
    }
    const newLimit = summary.length + 1;
    this.adjustSummary(newLimit).then(() => {
    });
  }

}