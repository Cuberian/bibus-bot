require: functions.js
require: slotfilling/slotFilling.sc
  module = sys.zb-common

theme: /

    state: Start
        q!: $regex</start>
        a: Молви, друг, и войди!
        
        state: Melon
            q: * (melon|мелон) *
            a: Перед тобой три коридора, в какой пойдешь?
        
            state: GoLeft:
                q: * *лев* *
                a: Тебя съел дракон! Не хотите сыграть снова?
                
                state: PlayAgain:
                    q: да
                    go!: /Start
            
            state: GoRight:
                q: * *прав* *
                a: Разрабы - дауны и не придумали эту сюжетную ветку(
            
            state: GoStraight:
                q: * *прям* *
                a: Перед вами сундук. Что будете делать?
                
                state: OpenChest:
                    q: * (~открывать|~вскрывать) *
                    script:
                        $session.money = getRandomInt(10);
                        $reactions.answer("Вы получили " + $session.money + " монет!");
                    a: Хотите купить меч?
                    
                    state: Cancel:
                        q: * *(~нет|~идти|~проходить|~возвращаться)* *
                        go!: ../../Continue
                        
                    state: BuySword:
                        q: * *(да|~дать)* *
                        script:
                            if($session.money - 10 < 0)
                            {
                                $reactions.answer("вы не можете купить этот меч, но хотите испытать удачу? я приумножу ваши деньги на рандомное число, что скажите?");
                            }
                            else 
                            {
                                $session.money -= 10;
                                $reactions.answer("Поздравляю с покупкой! теперь у вас осталось " + $session.money + " монет!")
                                $reactions.transition("../../Continue")
                            }
                     
                        state: AgreeLotery:
                            q: * *(да|~играть|~дать)* *
                            script:
                                $session.money *= getRandomInt(10)
                                $reactions.answer("У вас теперь " + $session.money + " монет!")
                                
                                if($session.money - 10 >= 0)
                                {
                                    $reactions.answer("Хотите купить меч?")
                                    $reactions.transition("../");
                                }
                                else 
                                {
                                    $reactions.answer("Вам все равно не хватает!")
                                    $reactions.transition("../../../Continue")
                                }
                
                state: Continue:
                    q: * *(~идти|~проходить|~возвращаться)* *
                    a: Ваше путешествие продолжается...
                    
        state: NoMelon
            event: noMatch
            a: На эльфийском...
    
    state: NoMatch
        event!: noMatch
        a: Попробуйте сказать это на эльфийском
    
   