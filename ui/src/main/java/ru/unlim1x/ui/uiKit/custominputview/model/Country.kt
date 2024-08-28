package ru.unlim1x.ui.uiKit.custominputview.model

import ru.unlim1x.wb_project.R

internal enum class Country(val code: String, val phoneCode: String, val flagPainterId: Int) {
    Russia("RU", "+7", R.drawable.ru_flag),
    Belarus("BY", "+375", R.drawable.by_flag),
    Kazakhstan("KZ", "+7", R.drawable.kz_flag),
    USA("US", "+1", R.drawable.us_flag),
    Georgia("GE", "+995", R.drawable.ge_flag),
    Turkey("TR", "+90", R.drawable.tr_flag),
    ArabEmirates("AE", "+971", R.drawable.ae_flag),
    GreatBritain("GB", "+44", R.drawable.gb_flag),
    SouthKorea("KR", "+82", R.drawable.kr_flag)
}

