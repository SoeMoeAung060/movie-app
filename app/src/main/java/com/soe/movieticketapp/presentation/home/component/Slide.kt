package com.soe.movieticketapp.presentation.home.component

import com.soe.movieticketapp.R

enum class Slide(
    val imageId: Int,
    val movieId: Int,
    val title: String,
    val overview: String
) {

    Slide1(
        imageId = R.drawable.image01,
        movieId = 420634,
        title = "Like a Dragon: Yakuza",
        overview = "Set in the fictional town of Kamurocho, the story unfolds across two intersecting timelines—1995 and 2005. In 1995, four friends eager to escape their restrictive lives in an orphanage dive into the yakuza-controlled underworld of 1995 Kamurocho. Meanwhile, in 2005, their friendship is deteriorated, as tensions between the Tojo Clan and the Omi Alliance are at a boiling point."
    ),

    Slide2(
        imageId = R.drawable.image02,
        movieId = 1182047,
        title = "he Apprentice",
        overview = "A young Donald Trump, eager to make his name as a hungry scion of a wealthy family in 1970s New York, comes under the spell of Roy Cohn, the cutthroat attorney who would help create the Donald Trump we know today. Cohn sees in Trump the perfect protégé—someone with raw ambition, a hunger for success, and a willingness to do whatever it takes to win."
    ),

    Slide3(
        imageId = R.drawable.image03,
        movieId = 976734,
        title = "Canary Black",
        overview = "Top level CIA agent Avery Graves is blackmailed by terrorists into betraying her own country to save her kidnapped husband. Cut off from her team, she turns to her underworld contacts to survive and help locate the coveted intelligence that the kidnappers want."
    ),

    Slide4(
        imageId = R.drawable.image04,
        movieId = 558449,
        title = "Gladiator II",
        overview = "Years after witnessing the death of the revered hero Maximus at the hands of his uncle, Lucius is forced to enter the Colosseum after his home is conquered by the tyrannical Emperors who now lead Rome with an iron fist. With rage in his heart and the future of the Empire at stake, Lucius must look to his past to find strength and honor to return the glory of Rome to its people."
    ),

    Slide5(
        imageId = R.drawable.image05,
        movieId = 402431,
        title = "Wicked",
        overview = "Elphaba, an ostracized but defiant girl born with green skin, and Glinda, a privileged aristocrat born popular, become extremely unlikely friends in the magical Land of Oz. As the two girls struggle with their opposing personalities, their friendship is tested as both begin to fulfill their destinies as Glinda the Good and The Wicked Witch of the West."
    ),

    Slide6(
        imageId = R.drawable.image06,
        movieId = 1399,
        title = "Game of Thrones",
        overview = "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond."
    ),

    Slid7(
        imageId = R.drawable.image07,
        movieId = 1091181,
        title = "Family Pack",
        overview = "When an old card game comes to life, a family jumps back in time to a medieval village where they must unmask werewolves to secure their return home."
    ),
}