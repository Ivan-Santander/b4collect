/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 */
// slightly modified version of https://github.com/facebook/react-native/blob/e028ac7af2d5b48860f01055f3bbacf91f6b6956/Libraries/NewAppScreen/components/LearnMoreLinks.js

import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import * as Linking from 'expo-linking';
import React from 'react';
import { Colors } from '../../shared/themes';

const links = [
  {
    id: 1,
    title: 'Ritmo Cardiaco',
    // link: 'https://github.com/jhipster/generator-jhipster-react-native/blob/main/docs/project-structure.md',
    description: "56 BPM",
  },
  {
    id: 2,
    title: 'Presión Arterial',
    // link: 'https://reactnative.dev/docs/tutorial',
    description: '120/82',
  },
  {
    id: 3,
    title: 'Oximetría',
    // link: 'https://reactnative.dev/docs/style',
    description: '98%',
  },
  {
    id: 4,
    title: 'Temperatura',
    // link: 'https://reactnative.dev/docs/flexbox',
    description: '36,6 C',
  },
  {
    id: 5,
    title: 'Pasos',
    // link: 'https://reactnative.dev/docs/components-and-apis',
    description: '2325 pasos',
  },
  {
    id: 6,
    title: 'Sueño Profundo',
    // link: 'https://reactnavigation.org/docs/getting-started',
    description: '4.32 horas',
  },
];

const LinkList = () => (
  <View style={styles.container}>
    {links.map(({ id, title, link, description }) => {
      return (
        <React.Fragment key={id}>
          <View style={styles.separator} />
          <TouchableOpacity accessibilityRole={'button'} onPress={() => Linking.openURL(link)} style={styles.linkContainer}>
            <Text style={styles.link}>{title}</Text>
            <Text style={styles.description}>{description}</Text>
          </TouchableOpacity>
        </React.Fragment>
      );
    })}
  </View>
);

const styles = StyleSheet.create({
  container: {
    paddingHorizontal: 24,
  },
  linkContainer: {
    flexWrap: 'wrap',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 8,
  },
  link: {
    flex: 2,
    fontSize: 18,
    fontWeight: '600',
    color: Colors.white,
  },
  description: {
    flex: 3,
    paddingVertical: 5,
    fontWeight: '400',
    fontSize: 18,
    color: Colors.white,
  },
  separator: {
    backgroundColor: Colors.light,
    height: 1,
  },
});

export default LinkList;
