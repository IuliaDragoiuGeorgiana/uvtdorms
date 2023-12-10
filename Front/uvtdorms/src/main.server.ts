import { bootstrapApplication } from '@angular/platform-browser';
import { config } from './app/app.config.server';
import { HomePageComponent } from './app/pages/home-page/home-page.component';

const bootstrap = () => bootstrapApplication(HomePageComponent, config);

export default bootstrap;
