describe('header.scala.html', function() {

  it('should contain in header same tabs', function() {
    browser.get('/');
    expect(element.all(by.css('md-tab-item span')).getText())
        .toEqual(['ФИЛЬТРЫ', 'ИЗМЕНИТЬ РАСПИСКУ', 'НОВАЯ РАСПИСКА', 'РАБОЧИЙ СТОЛ', 'АНАЛИТИКА', 'НАСТРОЙКИ', 'ПРОФИЛЬ', 'ВОЙТИ']);
  });

  it('should be disabled tabs', function() {
    expect(element.all(by.css('md-tab-item.md-disabled span')).getText())
        .toEqual(['ИЗМЕНИТЬ РАСПИСКУ', 'РАБОЧИЙ СТОЛ', 'АНАЛИТИКА', 'НАСТРОЙКИ', 'ПРОФИЛЬ', 'ВОЙТИ'] );
  });

  it('should be active filters-tab at filters-page', function() {
    element.all(by.css('md-tab-item')).first().click(); //click to Filters
    expect(element(by.css('md-tab-item.md-active span')).getText()).toEqual('ФИЛЬТРЫ');
  });

  it('should be active create-new-check-tab at create-new-check-page', function() {
    element.all(by.css('md-tab-item')).get(2).click(); //click to New Check
    expect(element(by.css('md-tab-item.md-active span')).getText()).toEqual('НОВАЯ РАСПИСКА');
  });

});