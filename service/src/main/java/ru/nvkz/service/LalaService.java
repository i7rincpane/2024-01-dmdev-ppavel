package ru.nvkz.service;

import ru.nvkz.util.LalaUtil;

public class LalaService {
   private final LalaUtil lalaUtil;

    public LalaService(LalaUtil lalaUtil) {
        this.lalaUtil = lalaUtil;
    }

    public String lalala(){
      return lalaUtil.lala()+"la";
    }
}
