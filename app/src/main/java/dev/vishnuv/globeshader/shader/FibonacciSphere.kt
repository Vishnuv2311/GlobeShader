package dev.vishnuv.globeshader.shader

import org.intellij.lang.annotations.Language

@Language("AGSL")
 const val FibonacciSphere_SHADER = """
    // Source: @XorDev https://twitter.com/XorDev/status/1475524322785640455
    uniform float2 size;
    uniform float time;
    
    vec4 main(vec2 FC) {
      vec4 o = vec4(0);
      vec2 p = vec2(0), c=p, u=FC.xy*2.-size.xy;
      float a;
      for (float i=0; i<4e2; i++) {
        a = i/2e2-1.;
        p = cos(i*2.4+time+vec2(0,11))*sqrt(1.-a*a);
        c = u/size.y+vec2(p.x,a)/(p.y+2.5);
        o += (cos(i+vec4(0,2,4,0))+1.)/dot(c,c)*(1.-p.y)/3e4;
      }
      return o;
    }
"""